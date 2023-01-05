package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.badlogic.gdx.Gdx.gl20;

public class HealthStat{
    public Stage stage;
    public Table table;

    public void CreateHealth(int x, int y, int a,  int b){

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);     //sets stage

        table = new Table();
        table.setFillParent(true);      //creates tables
        stage.addActor(table);

        Label.LabelStyle Health = new Label.LabelStyle();
        Health.font = new BitmapFont();

        Label HealthNum = new Label("HP: ", Health);
        HealthNum.setPosition(x,y);
        HealthNum.setSize(a, b);

        stage.addActor(HealthNum);

        SpriteBatch HeartBatch = new SpriteBatch();
        Texture HeartTexture = new Texture(Gdx.files.internal("Heart.png"));
        TextureRegion HeartRegion = new TextureRegion(HeartTexture);

        Sprite Heart1 = new Sprite(HeartRegion);
        Sprite Heart2 = new Sprite(HeartRegion);
        Sprite Heart3 = new Sprite(HeartRegion);
        Heart1.setPosition(x + 35, y + 25);
        Heart2.setPosition(x + 70, y + 25);
        Heart3.setPosition(x + 105, y+ 25);
        Heart1.setSize(50,50);
        Heart2.setSize(50,50);
        Heart3.setSize(50,50);

        HeartActors HP1 = new HeartActors(Heart1);
        HeartActors HP2 = new HeartActors(Heart2);
        HeartActors HP3 = new HeartActors(Heart3);

        stage.addActor(HP1);
        stage.addActor(HP2);
        stage.addActor(HP3);

        table.setDebug(true);

    }

    public class HeartActors extends Actor {
        private Sprite Heart;
        public HeartActors(Sprite Heart){
            this.Heart = Heart;
        }
        @Override
        public void draw(Batch batch, float parentAlpha) {
            Heart.draw(batch);
        }
    }


    public void renderHealth() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());     //renders stage
        stage.draw();
    }

}
