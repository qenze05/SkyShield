package com.skyshield.game.particles;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

public class BuildingExplosion {

    public static ParticleEffectPool effectPool;
    public static ParticleEffect particleEffect = new ParticleEffect();

    public static void initEffect() {
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particles/Explosion Enemy/Particle Park Explosion Enemy.p"), Particles.textureAtlas);
        effectPool = new ParticleEffectPool(particleEffect, 1, 100);
    }

}

