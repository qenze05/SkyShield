package com.skyshield.game.particles;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

public class RocketExplosion {

    public static ParticleEffectPool effectPool;
    public static ParticleEffect particleEffect = new ParticleEffect();

    public static void initEffect() {
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particles/Explosion/Particle Park Explosion.p"), Particles.textureAtlas);
        particleEffect.scaleEffect(0.65f);
        effectPool = new ParticleEffectPool(particleEffect, 1, 100);
    }

}

