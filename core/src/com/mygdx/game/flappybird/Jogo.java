package com.mygdx.game.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Jogo extends ApplicationAdapter
{
	//Texturas
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoBaixo;
	private Texture canoCima;

	//Variaveis do dispositivo
	private float larguraDispositivo;
	private float alturaDispositivo;

	//Variáveis de controle do pássaro
	private float posicaoInicialVerticalPassaro = 0;

	//Variáveis de controle dos canos
	private float posicaoInicialCanoVertical;
	private float posicaoInicialCanoHorizontal;
	private float espacoCano;
	private boolean passouCano = false;

	//Variáveis de incremento
	private int pontos = 0;
	private float gravidade = 0;
	private float variacao = 0;

	//Variável de texto para a pontuação
	BitmapFont textoPontuacao;

	//Variável de número aleatório
	private Random random;

	//Colliders
	private ShapeRenderer shapeRenderer;
	private Circle circuloPassaro;
	private Rectangle retanguloCanoCima;
	private Rectangle retanguloCanoBaixo;


	
	@Override
	public void create ()
	{
		InicializaTexturas();
		InicializaObjetos();
	}

	@Override
	public void render ()
	{
		DesenhaTexturas();
		VerificaEstadoDoJogo();
		DetectaColisao();
		ValidaPontos();
	}

	private void ValidaPontos()
	{
		//Se o cano já tiver passado pelo pássaro
		if (posicaoInicialCanoHorizontal < 50 - passaros[0].getWidth())
		{
			//Se já não tiver passado pelo cano
			if (!passouCano)
			{
				//Incrementa os pontos
				pontos++;
				//Muda a variável para verdadeiro
				passouCano = true;
			}
		}
	}

	private void DetectaColisao()
	{
		//Cria o collider do pássaro
		circuloPassaro.set(50 + passaros[0].getWidth() / 2,
				posicaoInicialVerticalPassaro + passaros[0].getHeight() / 2, passaros[0].getWidth() / 2);

		//Cria o collider do cano de baixo
		retanguloCanoBaixo.set(posicaoInicialCanoHorizontal,
				alturaDispositivo / 2 - canoBaixo.getHeight() - espacoCano / 2 + posicaoInicialCanoVertical,
				canoBaixo.getWidth(), canoBaixo.getHeight());

		//Cria o collider do cano de cima
		retanguloCanoCima.set(posicaoInicialCanoHorizontal,
				alturaDispositivo / 2 - canoCima.getHeight() - espacoCano / 2 + posicaoInicialCanoVertical,
				canoCima.getWidth(), canoCima.getHeight());

		//Variaveis pra detectar se o pássaro colidiu com eles
		boolean bateuCanoCima = Intersector.overlaps(circuloPassaro, retanguloCanoCima);
		boolean bateuCanoBaixo = Intersector.overlaps(circuloPassaro, retanguloCanoBaixo);

		//Se tiver batido em algum dos canos
		if (bateuCanoBaixo || bateuCanoCima)
		{
			//Aparece a mensagem "Bateu" no Log
			Gdx.app.log("Log", "Bateu");
		}
	}

	private void VerificaEstadoDoJogo()
	{
		//Se a posição inicial do cano na horizontal for menor do que o valor negativo da horizontal do cano cima
		if (posicaoInicialCanoHorizontal < -canoCima.getWidth())
		{
			//Posiciona o cano no final da largura do dispositivo
			posicaoInicialCanoHorizontal = larguraDispositivo;
			//Atribui um valor aleatório para a vertical do cano
			posicaoInicialCanoVertical = random.nextInt(400) - 200;
			//Muda para falso já que o pássaro terá passado pelo cano
			passouCano = false;
		}

		//Movimenta o cano pela fase
		posicaoInicialCanoHorizontal -= Gdx.graphics.getDeltaTime()*200;

		//Registra o toque na tela
		boolean toqueTela = Gdx.input.justTouched();

		//Se tocar o pássaro perde gravidade (o que vai fazer ele ir para cima)
		if (toqueTela)
			gravidade = -14;

		//Se ele estiver no ar ou tocar a tela será aplicada a gravidade
		if (posicaoInicialVerticalPassaro > 0 || toqueTela)
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;

		//Muda o sprite do pássaro conforme o tempo passa
		variacao += Gdx.graphics.getDeltaTime() * 10;
		if (variacao > 3)
			variacao = 0;

		//Incrementa a gravidade conforme o tempo passa
		gravidade++;

		//Não permite que a gravidade fique maior do que 20
		if (gravidade >= 20)
			gravidade = 20;
	}

	private void InicializaObjetos()
	{
		batch = new SpriteBatch();

		//Cria a variavel aleatória
		random = new Random();

		//Atribui os valores para a altura e largura do dispositivo
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();

		//Atribui os valores para a posição vertical do pássaro (no meio da tela)
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;
		//Posição inicial do cano na horizontal será o fim da tela do dispositivo
		posicaoInicialCanoHorizontal = larguraDispositivo;
		//Espaço entre o meio dos canos
		espacoCano = 150;

		//Cria o texto de pontuação, deixa branco e com escala de 10
		textoPontuacao = new BitmapFont();
		textoPontuacao.setColor(Color.WHITE);
		textoPontuacao.getData().setScale(10);

		//Cria os colliders
		shapeRenderer = new ShapeRenderer();
		circuloPassaro = new Circle();
		retanguloCanoCima = new Rectangle();
		retanguloCanoBaixo = new Rectangle();
	}

	private void InicializaTexturas()
	{
		//Inicializa textura do fundo
		fundo = new Texture("fundo.png");

		//Inicializa textura dos pássaros
		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		//Inicializa textura dos canos
		canoCima = new Texture("cano_topo_maior.png");
		canoBaixo = new Texture("cano_baixo_maior.png");
	}

	private void DesenhaTexturas()
	{
		batch.begin();

		//Desenha o background na tela e o deixa do tamanho da altura e largura do dispositivo
		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
		//Desenha o pássaro na tela e atribui uma das variações ao seu sprite (na posição 50 em X e no meio da tela em Y)
		batch.draw(passaros[(int) variacao], 50, posicaoInicialVerticalPassaro);

		//Desenha os canos no fim da tela e faz o calculo para posiciona-los de acordo com o espaço
		batch.draw(canoBaixo, posicaoInicialCanoHorizontal, alturaDispositivo /2 - canoBaixo.getHeight() - espacoCano/2 + posicaoInicialCanoVertical);
		batch.draw(canoCima, posicaoInicialCanoHorizontal, alturaDispositivo /2 + espacoCano + posicaoInicialCanoVertical);

		//Desenha a pontuação no meio da tela em X e no Y -100
		textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo / 2, alturaDispositivo - 100);

		batch.end();
	}
	
	@Override
	public void dispose ()
	{

	}
}
