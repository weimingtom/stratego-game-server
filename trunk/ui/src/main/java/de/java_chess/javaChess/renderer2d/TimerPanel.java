/*
  TimerPanel - A component to display a timer.

  Copyright (C) 2003 The Java-Chess team <info@java-chess.de>

  This program is free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public License
  as published by the Free Software Foundation; either version 2
  of the License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software
  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

package de.java_chess.javaChess.renderer2d;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


/**
 * This class implements the functionality to display a timer
 */
public class TimerPanel extends JPanel implements Runnable {

    // Static variables

    /**
     * The width of the display in pixels.
     */
    static final int _displayWidth = 70;

    /**
     * The height if of the display in pixels.
     */
    static final int _displayHeight = 16;


    // Instance variables

    /**
     * The action listeners.
     */
    private ArrayList _actionListeners;

    /**
     * The timer thread.
     */
    private volatile Thread _timerThread;

    /**
     * A label to display the time.
     */
    private JLabel _display;

    /**
     * The time left in milliseconds.
     */
    private long _time;

    /**
     * The remaining time until timeout.
     */
    long _remainingTime;

    /**
     * The direction of the counting.
     */
    private boolean _countdown;


    // Constructors

    /**
     * Create a new timer panel.
     *
     * @param countdown Flag to indicate, if we count downwards.
     */
    public TimerPanel( boolean countdown) {
	setCountingDirection( countdown);
	_actionListeners = new ArrayList();
	_display = new JLabel("0:00:00", JLabel.RIGHT);
	_display.setPreferredSize( new Dimension( _displayWidth, _displayHeight));
        _display.setHorizontalTextPosition(SwingConstants.CENTER);
	add( _display);
    }


    // Methods

    /**
     * Add a action listener to this timer.
     *
     * @param listener The action listener to add.
     */
    public void addActionListener( ActionListener listener) {
	_actionListeners.add( listener);
    }

    /**
     * Notify the waiting action listeners.
     *
     * @param event The action event to send.
     */
    private void notifyListeners( ActionEvent event) {

	// Iterate over all the listeners and send them the event.
	for( Iterator iterator = _actionListeners.iterator(); iterator.hasNext(); ) {
	    ActionListener listener = (ActionListener)iterator.next();
	    listener.actionPerformed( event);
	}
    }

    /**
     * Set the length of the countdown in seconds.
     *
     * @param time The length of the countdown.
     */
    public void setCountdown( int time) {
	_remainingTime = _time = 1000L * (long)time;
	display( _time);
    }

    /**
     * Start the timer.
     */
    public void start() {
	if( _time > 0L) {
	    if( _timerThread == null) {
		_timerThread = new Thread( this);
		_timerThread.start();
	    }
	}
    }

    /**
     * Stop the timer.
     */
    public void stop() {

	// If there is a running thread.
	if( _timerThread != null) {
	    Thread timerThread = _timerThread;  // Copy it.
	    _timerThread = null;                // Signal to stop the thread.
	    try {
		timerThread.join();             // And wait for the thread to end.
	    } catch( InterruptedException ignored) {}
	}
    }

    /**
     * The actual thread method.
     */
    public void run() {

	long startTime = System.currentTimeMillis();  // The time when this run started.
	long runningTime;                             // The length of this run.
	try {
	    // While there's still time left and noone stopped the thread.
	    do {
		Thread.sleep( 100);  // Wait 1/10 of a second.
		runningTime = System.currentTimeMillis() - startTime;
		display( _remainingTime - runningTime);  // decrease the time by a second and display it.
	    } while( ( _remainingTime >= runningTime) && (Thread.currentThread() == _timerThread));
	    
	    _remainingTime -= runningTime;  // Substract the length of this thinking time.

	    // Now notify all the action listeners, that the timer has stopped.
	    notifyListeners( new ActionEvent( this, ActionEvent.ACTION_PERFORMED, ( _remainingTime < 0L) ? "timeout" : "interrupted"));

	} catch( InterruptedException ignored) {}
    }

    /**
     * Display the current time.
     *
     * @param time The current time in milliseconds.
     */
    void display( long time) {

	// Don't display negative time.
	if( time < 0L) {
	    time = 0L;
	}

	if( ! isCountdown()) {
	    time = _time - time;
	}

	int timeSec = (int)( time / 1000L);  // Display accurate to  1 s.
	StringBuffer timeString = new StringBuffer();
	int hours = timeSec / 3600;
	timeString.append( "" + hours);
	int minutes = ( timeSec / 60) % 60;
	timeString.append( ( ( minutes < 10) ?  ":0" : ":") + minutes);
	int seconds = timeSec % 60;
	timeString.append( ( ( seconds < 10) ? ":0" : ":") + seconds);
	_display.setText( timeString.toString());
	_display.paintImmediately( 0, 0, _displayWidth, _displayHeight);
    }

    /**
     * Check, if this timer counts downwards.
     *
     * @return true, if the counter counts downwards.
     */
    private final boolean isCountdown() {
	return _countdown;
    }
    
    /**
     * Set the counting direction.
     *
     * @param countdown Flag to indicate, if we want a countdown.
     */
    private final void setCountingDirection( boolean countdown) {
	_countdown = countdown;
    }

    /**
     * Get the remaining time in seconds.
     *
     * @return The remaining time in seconds.
     */
    public int getRemainingTime() {
	return (int)( _time / 1000L);
    }

    public void alignText()
    {
      this._display.setHorizontalTextPosition(SwingConstants.CENTER);
      this._display.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
