package org.sgJoe.plugin;

/**
 * This is a constant pool. These parameter are often used by the
 * plugins. This class is not often used, and possibliy removed in
 * a later release.
 *
 * @author   $ Author: Aleksandar Babic  $
 * @version  $ Revision:             0.1 $
 * @date     $ Date: 29/11/2005 21:27:08 $
 */
public interface Constants
{
  public static final String LAST_SELECTED_OBJECT = "LAST_SELECTED_OBJECT";
  
  public static final int OP_POSITION = 0;
  public static final int OP_DIRECTION = 1;
  public static final int OP_ATTENUATION = 2;
  public static final int OP_SPREAD_ANGLE = 3;
  public static final int OP_CONCENTRATION = 4;
  public static final int OP_ENABLE = 5;
  public static final int OP_COLOR = 6;
  public static final int OP_HIDE_LIGHT_SYMBOLS = 7;
  
  public static final int OP_GRID_RESIZE = 8;
  public static final int OP_GRID_HIDE = 9;

  public static final int OP_TREE_RELOAD = 10;
  public static final int OP_TREE_EXPAND = 11;
  public static final int OP_TREE_COLLAPSE = 12;
  
  public static final int OP_SWITCH_NAV_TYPE = 15;
  public static final int OP_SWITCH_NAV_MODE = 16;
  
  public static final String PLUGIN_MESSAGE = "PLUGIN_MESSAGE";
  public static final String MESSAGE = "MESSAGE";
  public static final String CONTEXT = "CONTEXT";
  
  public static final String CONTEXT_DIALOG = "CONTEXT_DIALOG";
  public static final String CONTEXT_MESSAGE = "CONTEXT_MESSAGE";
  public static final String CONTEXT_PANEL = "CONTEXT_PANEL";
  
  public static final String LIGHT_GROUP = "LIGHT_GROUP";
  public static final String LIGHT_DIRECTION = "LIGHT_DIRECTION";
  
  public static final String PICK_RESULT = "PICK_RESULT";
  
}
