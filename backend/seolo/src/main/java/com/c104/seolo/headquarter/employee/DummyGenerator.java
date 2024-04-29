package com.c104.seolo.headquarter.employee;

import com.c104.seolo.headquarter.employee.enums.Teams;
import com.c104.seolo.headquarter.employee.enums.Titles;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

public class DummyGenerator {
    private static final Random random = new Random();

    // 생년월일과 입사일, 퇴사일 범위 설정
    // 생년월일과 입사일, 퇴사일 범위 설정
    private static final LocalDate startDate = LocalDate.of(1959, 4, 27);
    private static final LocalDate endDate = LocalDate.of(2024, 4, 27);
    private static HashMap<Integer, Integer> yearToCount = new HashMap<>();

    public static void main(String[] args) {
        String filePath = "output.txt";
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            out.println(generateInsertStatements(150));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 파일을 바로 열기
        try {
            File file = new File(filePath);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("데스크톱 지원이 없어 파일을 자동으로 열 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateInsertStatements(int count) {
        StringBuilder sb = new StringBuilder();
        Titles[] titles = Titles.values();
        Teams[] teams = Teams.values();

        sb.append("INSERT INTO employee (employee_num, company_code, employee_name, employee_title, " +
                "employee_team, employee_birthday, employee_thum, employee_join_date, employee_leave_date, created_at, updated_at)\nVALUES\n");

        for (int i = 1; i <= count; i++) {
            String companyCode = "SFY001KOR";
            String name = generateKoreanName();
            Titles title = weightedRandom(titles, new int[]{50, 30, 12, 6, 2});
            Teams team = teams[random.nextInt(teams.length)];
            LocalDate birthday = randomDate(1959, 2004);
            LocalDate joinDate = randomDate(birthday.getYear() + 18, 2023);
            LocalDate leaveDate = random.nextFloat() < 0.03 ? randomDate(joinDate.getYear() + 1, 2024) : null;

            // Generate employee_num based on the join date year
            int year = joinDate.getYear();
            yearToCount.putIfAbsent(year, 0);
            yearToCount.put(year, yearToCount.get(year) + 1);
            String employeeNum = String.format("%d%05d", year, yearToCount.get(year));

            sb.append(String.format("('%s', '%s', '%s', '%s', '%s', '%s', 'default', '%s', %s, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),\n",
                    employeeNum, companyCode, name, title.name(), team.name(), birthday, joinDate, leaveDate == null ? "NULL" : "'" + leaveDate + "'"));
        }

        sb.deleteCharAt(sb.lastIndexOf(",")); // 마지막 콤마 제거
        sb.append(";");

        return sb.toString();
    }

    private static String generateKoreanName() {
        String[] firstNames = {"김", "이", "박", "최", "정", "한", "조", "윤", "장", "임","오"};
        String[] lastNames = {"민수", "예은", "준호", "혜진", "태원", "지아", "서연", "하은", "도윤", "지우", "수민",
                "영철","성철","창기","미숙","영마","혜영","재용","부진","건희","혜서","종건","승호","현진","건","빈","진명","현비",
        "형찬","민상","유진","정민","나라","민철","지훈","석열","재인","명박","근혜","재명","우재"};
        return firstNames[random.nextInt(firstNames.length)] + lastNames[random.nextInt(lastNames.length)];
    }

    private static LocalDate randomDate(int startYear, int endYear) {
        int year = random.nextInt(endYear - startYear + 1) + startYear;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(LocalDate.of(year, month, 1).lengthOfMonth()) + 1;
        return LocalDate.of(year, month, day);
    }

    private static <T extends Enum<?>> T weightedRandom(T[] items, int[] weights) {
        int totalWeight = 0;
        for (int weight : weights) {
            totalWeight += weight;
        }
        int index = random.nextInt(totalWeight);
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i];
            if (index < sum) {
                return items[i];
            }
        }
        return items[items.length - 1]; // fallback
    }
}
