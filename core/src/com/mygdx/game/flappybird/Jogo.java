package com.mygdx.game.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Jogo extends ApplicationAdapter
{
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoBaixo;
	private Texture canoCima;

	private float larguraDispositivo;
	private float alturaDispositivo;

	private int movimentaX = 0;
	private int movimentaY = 0;

	private int movimenta = 0;
	private float posicaoInicialVertical = 0;
	private float posicaoInicialCano = 0;
	private float espacoCano;

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
		canoCima = new Texture("cano_topo_maior.png");
		canoBaixo = new Texture("cano_baixo_maior.png");

		espacoCano = 150;
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVertical = alturaDispositivo / 2;
	}

	@Override
	public void render ()
	{
		batch.begin();

		if (variacao > 3)
			variacao = 0;

		boolean toqueTela = Gdx.input.justTouched();

		if (toqueTela)
			gravidade = -14;

		if (posicaoInicialVertical > 0 || toqueTela)
			posicaoInicialVertical = posicaoInicialVertical - gravidade;

		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
		batch.draw(passaros[(int) variacao], 30, posicaoInicialVertical);

		variacao += Gdx.graphics.getDeltaTime() * 10;

		gravidade++;
		movimentaX++;
		movimentaY++;

		if (gravidade >= 20)
			gravidade = 20;

		if (posicaoInicialCano < -canoCima.getWidth())
			posicaoInicialCano = larguraDispositivo;

		batch.draw(canoBaixo,posicaoInicialCano, alturaDispositivo /2 - canoBaixo.getHeight() - espacoCano/2);
		batch.draw(canoCima,posicaoInicialCano, alturaDispositivo /2 + espacoCano);

		posicaoInicialCano -= Gdx.graphics.getDeltaTime()*200;

		batch.end();
	}
	
	@Override
	public void dispose ()
	{

	}
}
