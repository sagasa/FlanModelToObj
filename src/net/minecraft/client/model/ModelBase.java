package net.minecraft.client.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.flansmod.client.tmt.ModelRendererTurbo;

public class ModelBase {
	/** ModelRendererTurbo[][]を返す */
	public ModelRendererTurbo[][] getModels() {
		List<ModelRendererTurbo[]> list = new ArrayList<>();
		for (Field field : this.getClass().getFields()) {
			if (field.getType().equals(ModelRendererTurbo[].class)) {
				try {
					list.add((ModelRendererTurbo[]) field.get(this));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return list.toArray(new ModelRendererTurbo[list.size()][]);
	}

	/** 回転を適応 */
	public void compile() {
		for (ModelRendererTurbo[] model : getModels()) {
			compile(model);
		}
	}

	protected void compile(ModelRendererTurbo[] model) {
		for (ModelRendererTurbo part : model) {
			part.compile();
		}
	}

	public void flipAll() {
		for (ModelRendererTurbo[] model : getModels()) {
			flip(model);
		}
	}

	protected void flip(ModelRendererTurbo[] model) {
		for (ModelRendererTurbo part : model) {
			part.doMirror(false, true, true);
			part.setRotationPoint(part.rotationPointX, -part.rotationPointY, -part.rotationPointZ);
		}
	}

	public void translateAll(float x, float y, float z) {

	}
}
