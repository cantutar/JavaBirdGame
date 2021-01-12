package com.vahitcantutar.bird;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

public class BirdGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture bird;
    Texture enemy;
    Texture enemy1;
    Texture enemy2;
    int time = 0;
    float birdX;
    float birdY;
    int gameState = 0;
    float velocity = 0;
    float gravity = 0.3f;
    float enemyVelocity = 1;
    Random random;
    int score = 0;
    int scoredEnemy = 0;
    BitmapFont font;
    BitmapFont font2;

    Circle birdCircle;

    ShapeRenderer shapeRenderer;

    int numberOfEnemies = 4;
    float distance = 0;
    float[] enemyX = new float[numberOfEnemies];
    float[] enemyOffset = new float[numberOfEnemies];
    float[] enemyOffset1 = new float[numberOfEnemies];
    float[] enemyOffset2 = new float[numberOfEnemies];

    Circle[] enemyCircles;
    Circle[] enemyCircles1;
    Circle[] enemyCircles2;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        bird = new Texture("bird.png");
        enemy = new Texture("enemy.png");
        enemy1 = new Texture("enemy.png");
        enemy2 = new Texture("enemy.png");
        distance = Gdx.graphics.getWidth() / 2;
        random = new Random();
        birdX = Gdx.graphics.getWidth() / 5;
        birdY = Gdx.graphics.getHeight() / 2;

        shapeRenderer = new ShapeRenderer();

        birdCircle = new Circle();
        enemyCircles = new Circle[numberOfEnemies];
        enemyCircles1 = new Circle[numberOfEnemies];
        enemyCircles2 = new Circle[numberOfEnemies];

        font= new BitmapFont();
        font.setColor(Color.WHITE);
        font2= new BitmapFont();
        font.getData().setScale(4);
        font2.setColor(Color.WHITE);
        font2.getData().setScale(6);

        for (int i = 0; i < numberOfEnemies; i++) {
            enemyX[i] = Gdx.graphics.getWidth() - enemy.getWidth() / 3 + i * distance;

            enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

            enemyCircles[i] = new Circle();
            enemyCircles1[i] = new Circle();
            enemyCircles2[i] = new Circle();
        }

    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (gameState == 1) {
            time++;
            // zaman
            // System.out.println(time);
            if (Gdx.input.justTouched()) {
                velocity = -10;
            }
            if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 5){
                score++;
                if (scoredEnemy < numberOfEnemies-1) {
                    scoredEnemy++;
                } else {
                    scoredEnemy = 0;
                }
            }
            for (int i = 0; i < numberOfEnemies; i++) {
                // zaman
                if (time > 1) {
                    enemyVelocity = enemyVelocity + 0.0001f;
                    //System.out.println(enemyVelocity);
                }
                if (enemyX[i] < 0) {
                    enemyX[i] = enemyX[i] + numberOfEnemies * distance;

                    enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                } else {
                    enemyX[i] = enemyX[i] - enemyVelocity;
                }
                enemyX[i] = enemyX[i] - enemyVelocity;
                batch.draw(enemy, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset[i], Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8);
                batch.draw(enemy1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset1[i], Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8);
                batch.draw(enemy2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset2[i], Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8);

                enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffset[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 20);
                enemyCircles1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffset1[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 20);
                enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 2 + enemyOffset2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 20);
            }

            if (birdY > 0) {
                velocity = velocity + gravity;
                birdY = birdY - velocity;
            } else {
                gameState = 2;
            }

        } else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        } else if (gameState == 2) {
            font2.draw(batch,"Game over! Tap to play again!",100,Gdx.graphics.getHeight()/2);
            if (Gdx.input.justTouched()) {
                gameState = 1;
                birdY = Gdx.graphics.getHeight() / 2;
                for (int i = 0; i < numberOfEnemies; i++) {
                    enemyX[i] = Gdx.graphics.getWidth() - enemy.getWidth() / 3 + i * distance;

                    enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

                    enemyCircles[i] = new Circle();
                    enemyCircles1[i] = new Circle();
                    enemyCircles2[i] = new Circle();
                }
                velocity = 0;
                score=0;
                scoredEnemy=0;
            }
        }

        batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
        font.draw(batch,String.valueOf(score),100,200);
        batch.end();

        birdCircle.set(birdX + Gdx.graphics.getWidth() / 30, birdY + Gdx.graphics.getWidth() / 20,
                Gdx.graphics.getWidth() / 20);

        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(Color.BLACK);
        //shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);


        for (int i = 0; i < numberOfEnemies; i++) {
            //shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 20,Gdx.graphics.getHeight() / 2 + enemyOffset[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 20);
            //shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 20,Gdx.graphics.getHeight() / 2 + enemyOffset1[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 20);
            //shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 20,Gdx.graphics.getHeight() / 2 + enemyOffset2[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 20);
            if (Intersector.overlaps(birdCircle, enemyCircles[i]) || Intersector.overlaps(birdCircle, enemyCircles1[i]) || Intersector.overlaps(birdCircle, enemyCircles2[i])) {
                gameState = 2;
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
    }
}
