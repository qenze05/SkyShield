package com.skyshield.game.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Particles {
    public static TextureAtlas textureAtlas;

    public static void initAtlas() {
        textureAtlas = new TextureAtlas();
        textureAtlas.addRegion("cloud_1",new TextureRegion(new Texture(Gdx.files.internal("Burnout/cloud_1.png"))));
    }

}
