package com.skyshield.game.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.SkyShield;
import com.skyshield.game.gameObjects.rockets.Rocket;

import java.awt.*;

public class SmokeParticle extends Image {
    public ParticleEffectPool.PooledEffect effect;
    public Rocket target;

    public SmokeParticle(Rocket target){
        super(new Texture(Gdx.files.internal("Smoke/circle2.png")));
//        effect = Particles.smokeParticles.obtain();
        effect.setPosition(target.getHitbox().x,target.getHitbox().y);
        this.target = target;
//        this.effect.scaleEffect(0.5f);
//        addAction(Actions.moveTo(target.getHitbox().x - target.getHitbox().width/4, target.getHitbox().y - target.getHitbox().height/4,0.1f, Interpolation.sine));
    }

    public boolean checkTarget() {
        return (target == null || target.isEliminated());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(checkTarget()) return;
        super.draw(batch, parentAlpha);
        effect.draw(batch);
    }

    @Override
    public void act(float delta) {
        if(checkTarget()) {
            effect.dispose();
//            Particles.pooled.removeValue(this.effect, false);
        }
        if(getActions().size != 0 && getActions().get(0).getTarget() != null) super.act(delta);
        effect.setPosition(this.getWidth()/2+this.getX(),this.getHeight()/2+this.getY());
        effect.update(delta);
//        getActions().clear();
//        addAction(Actions.moveTo(target.getHitbox().x - target.getHitbox().width/4, target.getHitbox().y - target.getHitbox().height/4,0.1f, Interpolation.sine));
    }
}
