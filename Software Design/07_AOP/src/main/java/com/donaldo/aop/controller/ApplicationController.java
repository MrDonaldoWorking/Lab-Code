package com.donaldo.aop.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/run")
public class ApplicationController {
    private static final int LENGTH = 10000;
    private static final int WINDOW = 100;

    private List<Integer> generateList(final int n) {
        final List<Integer> arr = new ArrayList<>();
        final Random rand = new Random();
        for (int i = 0; i < n; ++i) {
            arr.add(rand.nextInt(n));
        }
        return arr;
    }

    @GetMapping("/bubble")
    public List<Integer> bubbleSort() {
        final List<Integer> arr = generateList(LENGTH);
        for (int cnt = 0; cnt < LENGTH; ++cnt) {
            for (int i = 1; i < LENGTH; ++i) {
                if (arr.get(i - 1) > arr.get(i)) {
                    final int temp = arr.get(i - 1);
                    arr.set(i - 1, arr.get(i));
                    arr.set(i, temp);
                }
            }
        }
        return arr.subList(0, WINDOW);
    }

    @GetMapping("/quick")
    public List<Integer> quickSort() {
        final List<Integer> arr = generateList(LENGTH);
        Collections.sort(arr);
        return arr.subList(0, WINDOW);
    }
}
