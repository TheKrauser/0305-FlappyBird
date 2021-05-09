package com.mygdx.game.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jogo extends ApplicationAdapter
{
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;

	private float larguraDispositivo;
	private float alturaDipositivo;

	private int movimentaX = 0;
	private int movimentaY = 0;

	private int movimenta = 0;
	private float posicaoInicialVertical = 0;

	private float gravidade = 0;
	private float variacao = 0;
	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		fundo = new Texture("fundo.png");
		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDipositivo = Gdx.graphics.getHeight();
		posicaoInicialVertical = alturaDipositivo / 2;
	}

	@Override
	public void render ()
	{
		batch.begin();

		if (variacao > 3)
			variacao = 0;

		boolean toqueTela = Gdx.input.justTouched();

		if (toqueTela)
			gravidade = -25;

		if (posicaoInicialVertical > 0 || toqueTela)
			posicaoInicialVertical = posicaoInicialVertical - gravidade;

		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDipositivo);
		batch.draw(passaros[(int) variacao], 30, posicaoInicialVertical);

		variacao += Gdx.graphics.getDeltaTime() * 10;

		gravidade++;
		movimentaX++;
		movimentaY++;

		batch.end();
	}
	
	@Override
	public void dispose ()
	{

	}
}
