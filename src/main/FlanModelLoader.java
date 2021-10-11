package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

import com.flansmod.client.model.ModelGun;
import com.flansmod.client.model.ModelVehicle;

import net.minecraft.client.model.ModelBase;

/**
 * パックのルートディレクトリでインスタンス化して使う
 */
public class FlanModelLoader {

    private static final String separator;

    static {
        separator = File.separator.equals("\\") ? "\\" + File.separator : File.separator;
    }

    private File rootDir;
    private URLClassLoader loader;

    public FlanModelLoader(File rootdir) {
        rootDir = rootdir;
        try {
            loader = URLClassLoader.newInstance(new URL[]{rootDir.toURI().toURL()});
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
    }


    public void convert(ModelType type, String str){
         convert(readModelInfo(type, new File(rootDir, str)));
    }

    private void convert(ModelInfo info) {
        String obj = null;
        File outDir = null;
        try {
            // Typeによって場合分け
            obj = loadModel(info);
            outDir = info.getOutDir(base);
        } catch (ClassNotFoundException e1) {
            System.out.println("model not found " + info.Name);
        }
        if (obj == null || outDir == null) {
            System.out.println("cant load model " + info.Name);
            return;
        }
        System.out.println("convert " +outDir.getAbsolutePath()+" "+info.Name);
        outDir.mkdirs();
        String[] split = info.ModelName.split("\\.");
        try {
            Files.write(new File(outDir, split[split.length - 1] + ".obj").toPath(),obj.getBytes(StandardCharsets.UTF_8));
            Files.write(new File(outDir, "texture.mtl").toPath(),ObjBuilder.getMtl(info.TextureName).getBytes(StandardCharsets.UTF_8));
            File txture = new File(rootDir, "assets/flansmod/skins/" + info.TextureName + ".png");
            if (txture.exists()) {
                // texture.mtl
                new File(outDir, info.TextureName + ".png").delete();
                Files.copy(txture.toPath(), new File(outDir, info.TextureName + ".png").toPath());
            } else {
                System.out.println("missing texture " + txture.getPath());
            }
        } catch (IOException e) {
            System.out.println("errer in writing model " + info.Name);
            e.printStackTrace();
        }
        System.out.println("load sucsessful " + info.Name);
    }

    File base = new File("./output/");//TODO

    public void readAll() {
        // 設定ファイルから読み込みモデルを出力
        List<ModelInfo> infolist = new ArrayList<>();
        for (File file : listAll(new File(rootDir, "/guns/"), new ArrayList<>())) {
            convert(readModelInfo(ModelType.GUN, file));
        }
        for (File file : listAll(new File(rootDir, "/vehicles/"), new ArrayList<>())) {
            convert(readModelInfo(ModelType.VEHICLE, file));
        }
    }

    private class ModelInfo {
        ModelType Type;
        String Name;
        String ModelName;
        String TextureName;
        float ModelScale;

        public ModelInfo(ModelType type, String name, String model, String tex, float scale) {
            Type = type;
            Name = name;
            ModelName = model;
            TextureName = tex;
            ModelScale = scale;
        }
        public File getOutDir(File base){
            return Type.getOutDir(base,Name);
        }
    }

    public enum ModelType {
        GUN(ModelGun.class), VEHICLE(ModelVehicle.class);
        final Class<?extends ModelBase> modelClass;
        ModelType(Class<?extends ModelBase> clazz){
            modelClass = clazz;
        }

        public File getOutDir(File base,String name){
            return new File(base,toString().toLowerCase()+"/" + name.replaceAll("/", "-") + "/");
        }
    }

    /**
     * 渡されたストリームからモデル関連の情報を取得する ストリームは閉じる
     */
    private ModelInfo readModelInfo(ModelType type, File file) {
        String name = null;
        String model = null;
        String texture = null;
        float modelScale = 1f;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(" ", 2);
                if (line.startsWith("//") || split.length != 2) {
                    continue;
                }
                if (split[0].trim().equalsIgnoreCase("Model")) {
                    String[] split1 = split[1].trim().split("\\.");
                    split1[split1.length - 1] = "Model" + split1[split1.length - 1];
                    model = String.join(".", split1);
                } else if (split[0].trim().equalsIgnoreCase("Texture")) {
                    texture = split[1].trim();
                } else if (split[0].trim().equalsIgnoreCase("ModelScale")) {
                    modelScale = Float.parseFloat(split[1].trim());
                } else if (split[0].trim().equalsIgnoreCase("Name")) {
                    name = split[1].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelInfo(type, name, model, texture, modelScale);
    }

    /**
     * 設定ファイルからobjモデルを返す
     */

    private String loadModel(ModelInfo info) throws ClassNotFoundException{
        if (info == null) {
            return null;
        }
        // モデル変換
        try {
            ModelBase model = (ModelBase) loader.loadClass("com.flansmod.client.model." + info.ModelName).newInstance();
            return model.getObj();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 再起検索
     */
    private static List<File> listAll(File root, List<File> collect) {
        if (!root.isDirectory()) {
            return collect;
        }
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                listAll(file, collect);
            } else if (file.isFile()) {
                collect.add(file);
            }
        }
        return collect;
    }
}
