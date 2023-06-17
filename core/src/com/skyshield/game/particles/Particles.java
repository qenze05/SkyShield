package com.skyshield.game.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;


public class Particles {
    public static TextureAtlas textureAtlas;
    public static Array<ParticleEffectPool.PooledEffect> explosionEffects = new Array<>();
    public static HashMap<Rectangle, ParticleEffectPool.PooledEffect> rocketTrailEffects = new HashMap<>();
    public static void initParticles() {
        if(textureAtlas != null) return;
        textureAtlas = new TextureAtlas();
        textureAtlas.addRegion("circle2",new TextureRegion(new Texture(Gdx.files.internal("particles/Explosion/circle2.png"))));
        textureAtlas.addRegion("explosion-1",new TextureRegion(new Texture(Gdx.files.internal("particles/Explosion/explosion-1.png"))));
        textureAtlas.addRegion("explosion-2",new TextureRegion(new Texture(Gdx.files.internal("particles/Explosion/explosion-2.png"))));
        textureAtlas.addRegion("explosion-3",new TextureRegion(new Texture(Gdx.files.internal("particles/Explosion/explosion-3.png"))));
        textureAtlas.addRegion("explosion-1",new TextureRegion(new Texture(Gdx.files.internal("particles/Explosion Enemy/explosion-1.png"))));
        textureAtlas.addRegion("explosion-2",new TextureRegion(new Texture(Gdx.files.internal("particles/Explosion Enemy/explosion-2.png"))));
        textureAtlas.addRegion("explosion-3",new TextureRegion(new Texture(Gdx.files.internal("particles/Explosion Enemy/explosion-3.png"))));
        textureAtlas.addRegion("circle2",new TextureRegion(new Texture(Gdx.files.internal("particles/Smoke/circle2.png"))));

        BuildingExplosion.initEffect();
        Smoke.initEffect();
        RocketExplosion.initEffect();
    }

    public static void addParticle(String name, Rectangle pos) {
        ParticleEffectPool.PooledEffect pooledEffect;
        switch (name.toLowerCase()) {
            case "building_explosion" -> {
                pooledEffect = BuildingExplosion.effectPool.obtain();
                pooledEffect.setPosition(pos.x+pos.width/2, pos.y+pos.height/2);
                Particles.explosionEffects.add(pooledEffect);
            }
            case "trail" -> {
                pooledEffect = Smoke.effectPool.obtain();
                pooledEffect.setPosition(pos.x+pos.width/2, pos.y+pos.height/2);
                Particles.rocketTrailEffects.put(pos, pooledEffect);
            }
            case "rocket_explosion" -> {
                pooledEffect = RocketExplosion.effectPool.obtain();
                pooledEffect.setPosition(pos.x+pos.width/2, pos.y+pos.height/2);
                Particles.explosionEffects.add(pooledEffect);
            }
            default -> {
                pooledEffect = Smoke.effectPool.obtain();
                pooledEffect.setPosition(pos.x+pos.width/2, pos.y+pos.height/2);
                Particles.explosionEffects.add(pooledEffect);
            }
        }

    }

}
