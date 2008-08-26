/*
  StatusPanel - The class for the status panel in the main window.

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
//////////////////////////////////////////////////////////////////////////////
package de.java_chess.javaChess.renderer2d;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.BorderFactory;
import java.awt.*;


/**
 * Class for the display of status information
 */

public class StatusPanel extends JPanel
{

/**
 * Label für Status-Text
 */
	private JLabel jlStatus = new JLabel();

/**
 * Label für Aktions-Text
 */
	private JLabel jlAction = new JLabel();

/**
 * Label für die Versionsnummer
 */
	private JLabel jlVersion = new JLabel();

	private Font textFont = new Font( "Serif", Font.PLAIN, 12 );

  private String sVersion = "";

  GridBagLayout gridBagLayout = new GridBagLayout();


  public StatusPanel()
  {

		try
		{
			jbInit();
    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
  }

  public StatusPanel(String sVersionNumber)
  {
    this.sVersion = sVersionNumber;

		try
		{
			jbInit();
    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
  }

/**
 * Die GUI nach Nomenklatur des JBuilder
 *
 * @throws Exception
 */
	private void jbInit() throws Exception
	{
		this.setLayout(gridBagLayout);
		jlAction.setBorder(BorderFactory.createLoweredBevelBorder());

		this.jlStatus.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white,
																											 Color.white, new Color(148, 145, 140),
																											 new Color(103, 101, 98)));
		this.jlStatus.setText(" Your turn! ");
		this.jlStatus.setForeground( Color.black );
		this.jlStatus.setFont( this.textFont );

		this.jlAction.setText("Placeholder for action(s) in progress");
		this.jlAction.setFont( this.textFont );
		this.jlAction.setForeground( Color.black );

		this.jlVersion.setBorder(BorderFactory.createLoweredBevelBorder());
		this.jlVersion.setText( "Version: " + this.sVersion );
		this.jlVersion.setFont( this.textFont );
		this.jlVersion.setForeground( Color.black );

    add(jlAction,   new GridBagConstraints(0, 0, 1, 1, 0.6, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    add(jlStatus,   new GridBagConstraints(1, 0, 1, 1, 0.2, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(jlVersion,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		setBackground( Color.lightGray );
 }

 public void setStatusText(String newStatusText)
 {
  this.jlStatus.setText( newStatusText );
  }

  public void setActionText(String newActionText)
  {
    this.jlAction.setText( newActionText );
  }

  public void setVersionInfo(String newVersionText)
  {
    this.jlVersion.setText( newVersionText );
  }
}