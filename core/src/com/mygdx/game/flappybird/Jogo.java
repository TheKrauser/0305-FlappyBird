package com.mygdx.game.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jogo extends ApplicationAdapter
{
	SpriteBatch batch;
	Texture passaro;
	Texture fundo;

	private float larguraDispositivo;
	private float alturaDipositivo;

	private int movimentaX = 0;
	private int movimentaY = 0;

	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		fundo = new Texture("fundo.png");
		passaro = new Texture("passaro.png");

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDipositivo = Gdx.graphics.getHeight();
	}

	@Override
	public void render ()
	{
		batch.begin();

		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDipositivo);
		batch.draw(passaro, movimentaX, movimentaY);

		batch.end();
	}
	
	@Override
	public void dispose ()
	{

	}
}
