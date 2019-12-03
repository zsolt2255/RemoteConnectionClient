package com.topin.services;

import com.topin.helpers.Log;
import com.topin.model.command.help.ProcessSingle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskListListener{
    private Process process;
    private List<String> taskLists = new ArrayList<>();

    public TaskListListener() {
        //this.taskLists.add(0, "count");
    }

    /**
     * @return List
     */
    public List<String> run() {
        try {
            this.process = new ProcessBuilder("tasklist.exe", "/fo", "csv", "/nh").start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(this.process.getInputStream());
        int count = 0;

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String name = parts[0].substring(1).replaceFirst(".$", "");
            String pid = parts[1].substring(1).replaceFirst(".$", "");
            String memUsage = parts[4].substring(1)
                    .replaceFirst(".$", "")
                    .replaceAll("k|K","")
                    .replaceAll("�","")
                    .trim();

            ProcessSingle processSingle = new ProcessSingle(pid, name, memUsage);
            taskLists.add(processSingle.toString());
            count++;
        }

        try {
            process.waitFor();
            //taskLists.set(0,"count:"+count);
        } catch (InterruptedException e) {
            Log.write(this).error(e.getMessage());
            e.printStackTrace();
        }

        Log.write(this).info("TaskListener successfully fetched"+taskLists);

        return taskLists;
    }
}
