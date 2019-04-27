import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BowlingGameTest {

	@Test
	void finalScoreAllMiss() {
		List<Character> rolls = Arrays.asList('-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(0, game.calculateScore());
	}

	@Test
	void finalScoreSecondRollMiss() {
		List<Character> rolls = Arrays.asList('9', '-', '9', '-', '9', '-', '9', '-', '9', '-', '9', '-', '9', '-', '9', '-', '9', '-', '9', '-');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(90, game.calculateScore());
	}

	@Test
	void finalScoreAllSpare() {
		List<Character> rolls = Arrays.asList('5', '/', '5', '/', '5', '/', '5', '/', '5', '/', '5', '/', '5', '/', '5', '/', '5', '/', '5', '/', '5');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(150, game.calculateScore());
	}

	@Test
	void finalScoreAllStrike() {
		List<Character> rolls = Arrays.asList('X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(300, game.calculateScore());
	}
	
	@Test
	void finalScoreMix10thFrameSpare() {
		List<Character> rolls = Arrays.asList('2', '3', '6', '-', 'X', '7', '/', '5', '4', '-', '-', '8', '1', '-', '9', 'X', '1', '/', 'X');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(113, game.calculateScore());
	}
	
	@Test
	void finalScoreMix10thFrameStrike() {
		List<Character> rolls = Arrays.asList('X', '-', '-', '4', '/', 'X', 'X', '3', '6', '-', '/', '-', '2', 'X',	'X', '1', '/');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(134, game.calculateScore());
	}


	@Test
	void intermediateScoreAllMiss() {
		List<Character> rolls = Arrays.asList('-', '-', '-', '-', '-', '-', '-', '-', '-', '-');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(0, game.calculateScore());
	}

	@Test
	void intermediateScoreSecondRollMiss() {
		List<Character> rolls = Arrays.asList('9', '-', '9', '-', '9', '-', '9', '-', '9', '-');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(45, game.calculateScore());
	}

	@Test
	void intermediateScoreAllSpare() {
		List<Character> rolls = Arrays.asList('5', '/', '5', '/', '5', '/', '5', '/', '5', '/');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(70, game.calculateScore());
	}

	@Test
	void intermediateScoreAllStrike() {
		List<Character> rolls = Arrays.asList('X', 'X', 'X', 'X', 'X');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(120, game.calculateScore());
	}
	
	@Test
	void intermediateScoreMix5thFrameSpare() {
		List<Character> rolls = Arrays.asList('1', '2', '3', '-', 'X', '5', '4', '6', '/');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(44, game.calculateScore());
	}
	
	@Test
	void intermediateMix5thFrameStrike() {
		List<Character> rolls = Arrays.asList('X', '-', '-', '4', '/', 'X', 'X');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(60, game.calculateScore());
	}
	
	@Test
	void intermediateMixPartial5thFrame() {
		List<Character> rolls = Arrays.asList('1', '2', '3', '-', 'X', '5', '4', '6');
		BowlingGame game = new BowlingGame(rolls);
		assertEquals(40, game.calculateScore());
	}
	
	@Test
	void invalidRoll() {
		List<Character> rolls = Arrays.asList('A');
		BowlingGame game = new BowlingGame(rolls);
        assertThrows(IllegalArgumentException.class, () -> game.calculateScore());
    }
}
