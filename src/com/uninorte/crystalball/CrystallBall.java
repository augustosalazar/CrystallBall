package com.uninorte.crystalball;

import java.util.Random;

public class CrystallBall {
	public String[] mAnswers = { "yes1", "yes2", "yes3", "no1", "no2", "no3",
			"maybe1", "maybe2", "maybe3"};
	
	public String getAnswer() {

		String answer = "";

		Random randomGenerator = new Random();
		int randomNumber = randomGenerator.nextInt(mAnswers.length);

		answer = mAnswers[randomNumber];

		return answer;

	}
}
