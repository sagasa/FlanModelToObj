package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flansmod.client.model.ModelGun;
import com.flansmod.client.model.ModelVehicle;
import com.flansmod.client.tmt.ModelRendererTurbo;
import types.model.Polygon;

/** パックのルートディレクトリでインスタンス化して使う */
public class FlanModelLoader {

	private static final String separator;

	static {
		separator = File.separator.equals("\\") ? "\\" + File.separator : File.separator;
	}

	private File rootDir;
	private URLClassLoader loader;

	private Map<String, String> ModelMap = new HashMap<>();

	public FlanModelLoader(File rootdir) {
		rootDir = rootdir;
		try {
			loader = URLClassLoader.newInstance(new URL[] { rootDir.toURI().toURL() });
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		// 設定ファイルから読み込み
		List<ModelInfo> infolist = new ArrayList<>();
		try {
			for (File file : listAll(new File(rootDir, "/guns/"), new ArrayList<>())) {
				infolist.add(readModelInfo(ModelType.GUN, new FileInputStream(file)));
			}
			for (File file : listAll(new File(rootDir, "/vehicles/"), new ArrayList<>())) {
				infolist.add(readModelInfo(ModelType.VEHICLE, new FileInputStream(file)));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Infoからモデルを出力
		for (ModelInfo info : infolist) {
			/*
			 * if (ModelNameMap.get(info.ModelName) == null) {
			 * System.out.println("model not bind " + info.Name); continue; } //
			 */
			String obj = null;
			File outDir = null;
			try {
				// Typeによって場合分け
				switch (info.Type) {
				case GUN:
					obj = loadGunModel(info);
					outDir = new File("./output/guns/" + info.Name.replaceAll("/", "-") + "/");
					break;
				case VEHICLE:

					obj = loadVehicleModel(info);

					outDir = new File("./output/vehicles/" + info.Name.replaceAll("/", "-") + "/");
					break;
				}
			} catch (ClassNotFoundException e1) {
				System.out.println("model not found " + info.Name);
			}
			if (obj == null || outDir == null) {
				System.out.println("cant load model " + info.Name);
				continue;
			}
			outDir.mkdirs();
			try {
				String[] split = info.ModelName.split("\\.");
				FileWriter modelwriter = new FileWriter(new File(outDir,  split[split.length-1]+ ".obj"));
				modelwriter.write(obj);
				modelwriter.close();

				FileWriter mtlwriter = new FileWriter(new File(outDir, "texture.mtl"));
				mtlwriter.write(ObjBilder.getMtl(info.TextureName));
				mtlwriter.close();

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
	}

	public String getModel(String name) {
		return ModelMap.get(name);
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
	}

	private enum ModelType {
		GUN, VEHICLE
	}

	/** 渡されたストリームからモデル関連の情報を取得する ストリームは閉じる */
	private ModelInfo readModelInfo(ModelType type, InputStream in) {
		String name = null;
		String model = null;
		String texture = null;
		float modelScale = 1f;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
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
			reader.close();
			in.close();
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
		return new ModelInfo(type, name, model, texture, modelScale);
	}

	/** 設定ファイルからobjモデルを返す */
	private String loadGunModel(ModelInfo modelInfo) throws ClassNotFoundException {

		if (modelInfo == null) {
			return null;
		}
		// モデル変換
		ModelGun model;
		ObjBilder bilder = new ObjBilder();
		try {
			model = (ModelGun) loader.loadClass("com.flansmod.client.model." + modelInfo.ModelName).newInstance();
			model.compile();
			for (Field field : ModelGun.class.getFields()) {
				if (field.getType().equals(ModelRendererTurbo[].class)) {
					bilder.addPart(toPolygon((ModelRendererTurbo[]) field.get(model)), field.getName());
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		return bilder.flash();
	}

	/** 設定ファイルからobjモデルを返す */
	private String loadVehicleModel(ModelInfo modelInfo) throws ClassNotFoundException {

		if (modelInfo == null) {
			return null;
		}
		// モデル変換
		ModelVehicle model;
		ObjBilder bilder = new ObjBilder();
		try {
			model = (ModelVehicle) loader.loadClass("com.flansmod.client.model." + modelInfo.ModelName).newInstance();
			model.compile();
			for (Field field : ModelVehicle.class.getFields()) {
				if (field.getType().equals(ModelRendererTurbo[].class)) {
					bilder.addPart(toPolygon((ModelRendererTurbo[]) field.get(model)), field.getName());
				}
			}
		} catch (NoSuchFieldError e) {
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
		return bilder.flash();
	}

	/** 再起検索 */
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

	/** RenderTurbo[] to DisplayPart */
	private static Polygon[] toPolygon(ModelRendererTurbo[] modeles) {
		ArrayList<Polygon> polyList = new ArrayList<>();
		for (ModelRendererTurbo model : modeles) {
			polyList.addAll(model.Poly);

		}
		return polyList.toArray(new Polygon[polyList.size()]);
	}
}
