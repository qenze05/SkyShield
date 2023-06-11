package com.skyshield.game.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.utils.CountryTerritory;

public class LockedMapParticle extends Image {

    ParticleEffect effect;
    public static Array<LockedMapParticle> particles = new Array<>();

    public static Action getAction() {
        float[] first = getParticlePos();
        float[] second = getParticlePos();
        float[] third = getParticlePos();
        float[] fourth = getParticlePos();
        return Actions.repeat(10,Actions.sequence(
                Actions.moveTo(first[0]+MathUtils.random(-50, 50),
                        first[1]+MathUtils.random(-50, 50), 30, Interpolation.sineOut),
                Actions.moveTo(second[0]+MathUtils.random(-50, 50),
                        second[1]+MathUtils.random(-50, 50), 30, Interpolation.sineOut),
                Actions.moveTo(third[0]+MathUtils.random(-50, 50),
                        third[1]+MathUtils.random(-50, 50), 30, Interpolation.sineOut),
                Actions.moveTo(fourth[0]+MathUtils.random(-50, 50),
                        fourth[1]+MathUtils.random(-50, 50), 30, Interpolation.sineOut)));
    }

    public static float[] getParticlePos() {
        float[] pos = new float[]{1000, 600};
        switch (CountryTerritory.territory) {
            case 1 -> {
                return ter1[MathUtils.random(0, ter1.length-1)];
            }
            case 2 -> {
                return ter2[MathUtils.random(0, ter2.length-1)];
            }
            case 3 -> {
                return ter3[MathUtils.random(0, ter3.length-1)];
            }
            case 4 -> {
                return ter4[MathUtils.random(0, ter4.length-1)];
            }
            case 5 -> {
                return ter5[MathUtils.random(0, ter5.length-1)];
            }
            case 6 -> {
                return ter6[MathUtils.random(0, ter6.length-1)];
            }
        }
        return pos;
    }

    public LockedMapParticle(){
        super(new Texture("Burnout/cloud_1.png"));
        float[] pos = getParticlePos();
        setPosition(pos[0], pos[1]);
        addAction(getAction());
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("Burnout/Particle Park Burnout.p"), Particles.textureAtlas);
        effect.start();
        effect.setPosition(this.getWidth()/2+this.getX(),this.getHeight()/2+this.getY());

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        effect.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effect.setPosition(this.getWidth()/2+this.getX(),this.getHeight()/2+this.getY());
        effect.update(delta);

    }

    public static float[][] ter1 = new float[][]{
            {525, 600}, {670, 574}, {730, 523}, {826, 486},
            {910, 348}, {925, 283}, {1046, 224}, {1100, 290}
    };
    public static float[][] ter2 = new float[][]{
            {525, 600}, {670, 574}, {730, 523}, {826, 486},
            {910, 348}, {925, 283}, {1046, 224}, {1100, 290}
    };
    public static float[][] ter3 = new float[][]{
            {525, 600}, {670, 574}, {730, 523}, {826, 486},
            {910, 348}, {925, 283}, {1046, 224}, {1100, 290}
    };
    public static float[][] ter4 = new float[][]{
            {525, 600}, {670, 574}, {730, 523}, {826, 486},
            {910, 348}, {925, 283}, {1046, 224}, {1100, 290}
    };
    public static float[][] ter5 = new float[][]{
            {525, 600}, {670, 574}, {730, 523}, {826, 486},
            {910, 348}, {925, 283}, {1046, 224}, {1100, 290}
    };
    public static float[][] ter6 = new float[][]{
            {525, 600}, {670, 574}, {730, 523}, {826, 486},
            {910, 348}, {925, 283}, {1046, 224}, {1100, 290}
    };
}