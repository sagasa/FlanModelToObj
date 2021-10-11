package net.minecraft.client.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.function.BiConsumer;

import com.flansmod.client.tmt.ModelRendererTurbo;
import main.ObjBuilder;
import types.model.Polygon;

public class ModelBase {

    private static Polygon[] toPolygon(ModelRendererTurbo[] modeles) {
        ArrayList<Polygon> polyList = new ArrayList<>();
        for (ModelRendererTurbo model : modeles) {
            polyList.addAll(model.compile());
        }
        return polyList.toArray(Polygon[]::new);
    }


    /**
     * ModelRenderを全選択
     */
    private void applyFunc(BiConsumer<String, ModelRendererTurbo[]> cons) {
        try {
            for (Field field : this.getClass().getFields()) {
                //1次元
                if (field.getType().equals(ModelRendererTurbo[].class)) {
                    cons.accept(field.getName(), (ModelRendererTurbo[]) field.get(this));
                }
                //2次元
                if (field.getType().equals(ModelRendererTurbo[][].class)) {
                    ModelRendererTurbo[][] modelArray = (ModelRendererTurbo[][]) field.get(this);
                    for (int i = 0; i < modelArray.length; i++) {
                        cons.accept(field.getName() + "_" + i, modelArray[i]);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String getObj() {
        ObjBuilder builder = new ObjBuilder();
        applyFunc((name, value) -> builder.addPart(name, toPolygon(value)));
        return builder.flash();
    }

    public void flipAll() {
        applyFunc((name, value) -> flip(value));
    }

    protected void flip(ModelRendererTurbo[] model) {
        for (ModelRendererTurbo part : model) {
            part.doMirror(false, true, true);
            part.setRotationPoint(part.rotationPointX, -part.rotationPointY, -part.rotationPointZ);
        }
    }

    protected void translate(ModelRendererTurbo[] model, float x, float y, float z)
    {
        for(ModelRendererTurbo mod : model)
        {
            mod.rotationPointX += x;
            mod.rotationPointY += y;
            mod.rotationPointZ += z;
        }
    }

    public void translateAll(float x, float y, float z)
    {
        applyFunc((name, value) -> translate(value, x, y, z));
    }

    public void translateAll(int x, int y, int z)
    {
        translateAll((float)x, (float)y, (float)z);
    }
}
