package gameEngine;

import java.time.Duration;
import java.time.LocalDateTime;

public class GameTime {

	private byte seconds = 0;
	private byte minutes = 0;
	private int hours = 0;
	private LocalDateTime dateTime;
	
	/**
	 * Runs the Game timer in the game
	 */
	public GameTime() {
		dateTime = LocalDateTime.now();
	} 	
	
	/**
	 * Saves the current game time to the player object
	 * @param player The player object to save the time to
	 */
	public void SaveGameTime(Player player) {
		player.setSeconds(this.seconds);
		player.setMinutes(this.minutes);
		player.setHours(this.hours);
	}
	
	/**
	 * Tacks the current game time to the player object time
	 * @param player The player object to tack the time to
	 * @return The total game time as a string
	 */
	public String TackTime(Player player) {
		
		//Gets the current time
		LocalDateTime currentTime = LocalDateTime.now();
		
		//Calculates the duration between the two times
		Duration duration = Duration.between(dateTime, currentTime);
		
		//Adds the player's saved time to the duration
		duration = duration.plusSeconds(player.getSeconds());
		duration = duration.plusMinutes(player.getMinutes());
		duration = duration.plusHours(player.getHours());
		
		//Converts the duration to total seconds
		long totalSeconds = duration.getSeconds();
		
		//Calculates hours, minutes, and seconds
		while(totalSeconds >= 60) {
			totalSeconds -= 60;
			minutes++;
			if(minutes >= 60) {
				minutes = 0;
				hours++;
			}
		}
		seconds += totalSeconds;
		
		//Formats the time as a string
		String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		seconds = 0;
		minutes = 0;
		hours = 0;
		return timeString;
		
	}
	/**
	 * @return the seconds
	 */
	public byte getSeconds() {
		return seconds;
	}
	/**
	 * @param seconds the seconds to set
	 */
	public void setSeconds(byte seconds) {
		this.seconds = seconds;
	}
	/**
	 * @return the minutes
	 */
	public byte getMinutes() {
		return minutes;
	}
	/**
	 * @param minutes the minutes to set
	 */
	public void setMinutes(byte minutes) {
		this.minutes = minutes;
	}
	/**
	 * @return the hours
	 */
	public int getHours() {
		return hours;
	}
	/**
	 * @param hours the hours to set
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}	
}
