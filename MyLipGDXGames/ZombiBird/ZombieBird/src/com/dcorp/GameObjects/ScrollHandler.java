package com.dcorp.GameObjects;

import java.util.Random;

import com.dcorp.GameWorld.GameWorld;
import com.dcorp.ZBHelpers.AssetLoader;

public class ScrollHandler {

	private Grass frontGrass, backGrass;
	private Pipe pipe1;
	private Candys candy1;
	private Candys candy2;

	// private Pipe pipe2;
	private Pipe pipe3;
	public static final int SCROLL_SPEED = -59;
	public static final int PIPE_GAP = 143;

	private GameWorld gameWorld;

	public ScrollHandler(GameWorld gameWorld, float yPos) {
		this.gameWorld = gameWorld;
		frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
		backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11,
				SCROLL_SPEED);

		pipe1 = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
		candy1 = new Candys(pipe1.getTailX() + PIPE_GAP / 2, 50, 17, 12,
				SCROLL_SPEED, yPos);
		// pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 70,
		// SCROLL_SPEED,
		// yPos);
		// pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60,
		// SCROLL_SPEED,
		// yPos);
		pipe3 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED,
				yPos);
		candy2 = new Candys(pipe3.getTailX() + PIPE_GAP / 2, 50, 17, 12,
				SCROLL_SPEED, yPos);
	}

	public void updateReady(float delta) {

		frontGrass.update(delta);
		backGrass.update(delta);

		// Same with grass
		if (frontGrass.isScrolledLeft()) {
			frontGrass.reset(backGrass.getTailX());

		} else if (backGrass.isScrolledLeft()) {
			backGrass.reset(frontGrass.getTailX());

		}

	}

	public void update(float delta) {
		// Update our objects
		frontGrass.update(delta);
		backGrass.update(delta);
		pipe1.update(delta);
		candy1.update(delta);
		// pipe2.update(delta);
		pipe3.update(delta);
		candy2.update(delta);

		// Check if any of the pipes are scrolled left,
		// and reset accordingly
		if (pipe1.isScrolledLeft()) {
			pipe1.reset(pipe3.getTailX() + PIPE_GAP);
			candy2.reset(pipe3.getTailX() + PIPE_GAP / 2);

			// } else if (pipe2.isScrolledLeft()) {
			// pipe2.reset(pipe1.getTailX() + PIPE_GAP);

		} else if (pipe3.isScrolledLeft()) {
			// pipe3.reset(pipe2.getTailX() + PIPE_GAP);
			pipe3.reset(pipe1.getTailX() + PIPE_GAP);
			candy1.reset(pipe1.getTailX() + PIPE_GAP / 2);
		}

		// Same with grass
		if (frontGrass.isScrolledLeft()) {
			frontGrass.reset(backGrass.getTailX());

		} else if (backGrass.isScrolledLeft()) {
			backGrass.reset(frontGrass.getTailX());

		}
	}

	public void stop() {
		frontGrass.stop();
		backGrass.stop();
		pipe1.stop();
		candy1.stop();
		candy2.stop();
		// pipe2.stop();
		pipe3.stop();
	}

	public boolean collides(Bird bird) {

		if (!pipe1.isScored()
				&& pipe1.getX() + (pipe1.getWidth() / 2) < bird.getX()
						+ bird.getWidth()) {
			addScore(1);
			pipe1.setScored(true);
			AssetLoader.coin.play();
			// } else if (!pipe2.isScored()
			// && pipe2.getX() + (pipe2.getWidth() / 2) < bird.getX()
			// + bird.getWidth()) {
			// addScore(1);
			// pipe2.setScored(true);
			// AssetLoader.coin.play();

		} else if (!pipe3.isScored()
				&& pipe3.getX() + (pipe3.getWidth() / 2) < bird.getX()
						+ bird.getWidth()) {
			addScore(1);
			pipe3.setScored(true);
			AssetLoader.coin.play();

		}

		// return (pipe1.collides(bird) || pipe2.collides(bird) || pipe3
		// .collides(bird));
		return (pipe1.collides(bird) || pipe3.collides(bird));
	}

	public boolean collectBonus(Bird bird) {

		if (candy1.collides(bird)) {
			if (!candy1.isScored()) {
				candy1.setScored(true);
				AssetLoader.coin.play();
				addScore(1);
				return true;
			} else {
				return true;
			}
		} else if (candy2.collides(bird)) {
			if (!candy2.isScored()) {
				candy2.setScored(true);
				AssetLoader.coin.play();
				addScore(1);
				return true;
			} else {
				return true;
			}
		} else {
			return false;
		}

	}

	private void addScore(int increment) {
		gameWorld.addScore(increment);
	}

	public Grass getFrontGrass() {
		return frontGrass;
	}

	public Grass getBackGrass() {
		return backGrass;
	}

	public Pipe getPipe1() {
		return pipe1;
	}

	// public Pipe getPipe2() {
	// return pipe2;
	// }

	public Pipe getPipe3() {
		return pipe3;
	}

	public void onRestart() {
		frontGrass.onRestart(0, SCROLL_SPEED);
		backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
		pipe1.onRestart(210, SCROLL_SPEED);
		candy1.onRestart(pipe1.getTailX() + PIPE_GAP / 2, SCROLL_SPEED);
		// pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
		// pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
		pipe3.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
		candy2.onRestart(pipe3.getTailX() + PIPE_GAP / 2, SCROLL_SPEED);
	}

	public Candys getCandy() {
		return candy1;
	}

	public void setCandy(Candys candy) {
		this.candy1 = candy;
	}

	public Candys getCandy2() {
		return candy2;
	}

	public void setCandy2(Candys candy2) {
		this.candy2 = candy2;
	}

}
