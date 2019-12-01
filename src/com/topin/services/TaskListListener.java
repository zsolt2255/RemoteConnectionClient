package com.topin.services;

import com.topin.model.command.help.ProcessSingle;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskListListener{
    Process process;
    List<String> taskLists = new ArrayList<>();
    JSONObject jsonObject = new JSONObject();

    public TaskListListener() {
        this.process = null;
        //this.taskLists.add(0, "count");
    }

    public List<String> run() {
        try {
            this.process = new ProcessBuilder("tasklist.exe", "/fo", "csv", "/nh").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(this.process.getInputStream());
        int count = 0;
        if (scanner.hasNextLine()) scanner.nextLine();
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
            e.printStackTrace();
        }
        return taskLists;
    }
}
