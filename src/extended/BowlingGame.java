package extended;
import java.util.LinkedList;
import java.util.List;

public class BowlingGame {

	private static int maxFrameNumber = 10;
	
	private int frameNumber = 1;
	private TempCalculInfo lastCalculInfo = new TempCalculInfo();
	private LinkedList<RollInfo> rollInfoList = new LinkedList<>();

	public BowlingGame(List<Character> rollList) {
		updateRollInfoList(rollList);
	}

	public void addRolls(List<Character> additionalRollList) {
		updateRollInfoList(additionalRollList);
	}
	
	/**
	 * Calculate the score of the known rolls.
	 */
	public int calculateScore() {
		return rollInfoList.stream().mapToInt(rollInfo -> rollInfo.rollMultiplier * rollInfo.rollValue).sum(); 
	}

	/**
	 * Add the informations of the new rolls to the list used to calculate the score. 
	 */
	private void updateRollInfoList(List<Character> rollList) {
		TempCalculInfo calculInfo = lastCalculInfo;

		// Associate each roll to a value for score and determine its multiplier (because of spares and strikes).
		for (Character roll : rollList) {
			RollInfo rollInfo = initRollInfo(frameNumber, calculInfo.nextRollMultiplier);

			calculInfo = updateCalculInfo(calculInfo, roll, rollInfo.isBonusRoll, rollInfoList);

			rollInfo.rollValue = calculInfo.rollValue;
			rollInfoList.add(rollInfo);

			// Manage the frame number necessary to know if the roll is a bonus one.
			if (calculInfo.changeFrame) {
				frameNumber++;
			}
			// If the frame has not change this for this roll it has to change after the next one as there is maximum two rolls per frame.
			calculInfo.changeFrame = !calculInfo.changeFrame;
		}

		lastCalculInfo = calculInfo;
	}

	/**
	 * Initialize the RollInfo object with the multiplier of the roll and the flag to know if it is a bonus one.
	 */
	private RollInfo initRollInfo(int frameNumber, int foreseenRollMultiplier) {
		RollInfo rollInfo = new RollInfo();

		rollInfo.isBonusRoll = frameNumber > maxFrameNumber;

		if (!rollInfo.isBonusRoll) {
			rollInfo.rollMultiplier = foreseenRollMultiplier;
		} else {
			// For bonus roll its value only count for the score of the last regular frame :
			// if it was supposed to count thrice it will only count twice and in the other cases it will only count once instead of twice.
			rollInfo.rollMultiplier = foreseenRollMultiplier - 1;
		}

		return rollInfo;
	}

	/**
	 * Update the current calculation informations with a new object (changeFrame, nextRollMultiplier, nextSecondRollCountTwice, rollValue).
	 */
	private TempCalculInfo updateCalculInfo(TempCalculInfo oldCalculInfo, Character roll, boolean isBonusRoll, LinkedList<RollInfo> rollInfoList) throws IllegalArgumentException {
		TempCalculInfo calculInfo = new TempCalculInfo();
		calculInfo.changeFrame = oldCalculInfo.changeFrame;
		
		// Time to update the second next roll info to the next one.
		calculInfo.nextRollMultiplier = oldCalculInfo.nextSecondRollCountTwice ? 2 : 1;
		calculInfo.nextSecondRollCountTwice = false;

		switch (roll) {
			case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':
				calculInfo.rollValue = Character.getNumericValue(roll);
				break;
			case '/':
				calculInfo.rollValue = 10 - rollInfoList.getLast().rollValue;
				// As per the rules a spare can never be follow by a *3 roll, so no need to check the current value of nextRollMultiplier.
				calculInfo.nextRollMultiplier = isBonusRoll ? 1 : 2; // Side note : could be = 2 directly but this seems better in case of further developments
				calculInfo.changeFrame = true;
				break;
			case 'X':
				calculInfo.rollValue = 10;
				if (!isBonusRoll) {
					// If a strike follows a strike the next roll will be *3.
					calculInfo.nextRollMultiplier = calculInfo.nextRollMultiplier == 2 ? 3 : 2;
					calculInfo.nextSecondRollCountTwice = true;
				}
				calculInfo.changeFrame = true;
				break;
			case '-':
				calculInfo.rollValue = 0;
				break;
			default:
				throw new IllegalArgumentException("Illegal roll: " + roll); // Side note : not asked by the kata but simple to add with the way I did it
		}
		
		return calculInfo;
	}

	private class RollInfo {
		boolean isBonusRoll;
		int rollMultiplier;
		int rollValue;
	}

	private class TempCalculInfo {
		boolean changeFrame;
		int nextRollMultiplier = 1;
		boolean nextSecondRollCountTwice;
		int rollValue;
	}
}
