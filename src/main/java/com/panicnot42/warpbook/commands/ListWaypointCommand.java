package com.panicnot42.warpbook.commands;

import com.panicnot42.warpbook.WarpWorldStorage;
import com.panicnot42.warpbook.util.CommandUtils;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;

public class ListWaypointCommand extends CommandBase
{
  @Override
  public String getCommandName()
  {
    return "waypointlist";
  }

  @Override
  public String getCommandUsage(ICommandSender var1)
  {
    return "/waypointlist [page]";
  }

  @Override
  public void processCommand(ICommandSender var1, String[] var2)
  {
    WarpWorldStorage storage = WarpWorldStorage.instance(var1.getEntityWorld());
    int page;
    try
    {
      page = var2.length == 0 ? 0 : CommandBase.parseInt(var1, var2[0]);
      String[] wps = storage.listWaypoints();
      if (wps.length == 0)
      {
        CommandUtils.showError(var1, I18n.format("help.nowaypointsfound"));
        return;
      }
      CommandUtils.info(var1, String.format("-- Page %d --", page));
      for (int i = page * 8; i < ((page * 8) + 9); ++i)
      {
        try
        {
          CommandUtils.info(var1, wps[i]);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
        }
      }
      CommandUtils.info(var1, String.format("-- Page %d --", page));
    }
    catch (NumberInvalidException e)
    {
      CommandUtils.printUsage(var1, this);
      CommandUtils.showError(var1, CommandUtils.ChatType.TYPE_int, ((String)e.getErrorOjbects()[0]));
    }
  }

  @Override
  public int compareTo(Object o)
  {
    return 42; // TODO: actually implement this
  }
}
