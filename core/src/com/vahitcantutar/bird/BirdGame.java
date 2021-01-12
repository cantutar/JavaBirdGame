package com.vahitcantutar.bird;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    int numberOfEnemies = 4;
    float distance = 0;
    float[] enemyX = new float[numberOfEnemies];
    float[] enemyOffset = new float[numberOfEnemies];
    float[] enemyOffset1 = new float[numberOfEnemies];
    float[] enemyOffset2 = new float[numberOfEnemies];

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        bird = new Texture("bird.png");
        enemy = new Texture("enemy.png");
        enemy1 = new Texture("enemy.png");
        enemy2 = new Texture("enemy.png");
        // boolean gameStart = false;
        distance = Gdx.graphics.getWidth() / 2;
        random = new Random();
        birdX = Gdx.graphics.getWidth() / 5;
        birdY = Gdx.graphics.getHeight() / 2;

        for (int i = 0; i < numberOfEnemies; i++) {
            enemyX[i] = Gdx.graphics.getWidth() - enemy.getWidth() / 3 + i * distance;

            enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
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
            for (int i = 0; i < numberOfEnemies; i++) {
                // zaman
                // if (time > 1) {
                // enemyVelocity = enemyVelocity + 0.0001f;
                // System.out.println(enemyVelocity);
                // }
                if (enemyX[i] < 0) {
                    enemyX[i] = enemyX[i] + numberOfEnemies * distance;

                    enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                } else {
                    enemyX[i] = enemyX[i] - enemyVelocity;
                }
                enemyX[i] = enemyX[i] - enemyVelocity;
                batch.draw(enemy, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset[i],
                        Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8);
                batch.draw(enemy1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset1[i],
                        Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8);
                batch.draw(enemy2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset2[i],
                        Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 8);
            }

            if (birdY > 0 || velocity < 0) {
                velocity = velocity + gravity;
                birdY = birdY - velocity;
            }

        } else {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        }

        batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
        batch.end();
    }

    @Override
    public void dispose() {
    }
}
