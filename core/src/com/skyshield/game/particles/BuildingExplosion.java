package com.skyshield.game.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.utils.Array;

public class BuildingExplosion {

    public static ParticleEffectPool effectPool;
    public static Array<ParticleEffectPool.PooledEffect> effects = new Array<>();
    public static ParticleEffect effect = new ParticleEffect();

    public static void addEffect() {
//        ParticleEffect bombEffect = new ParticleEffect();
//        bombEffect.load(Gdx.files.internal("particles/explosion"), atlas);
    }
}
