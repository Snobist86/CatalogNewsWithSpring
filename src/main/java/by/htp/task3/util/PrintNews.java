package by.htp.task3.util;

import by.htp.task3.entity.News;

import java.util.List;

public class PrintNews {
	
	public static void print(List<News> news) {
		if (news == null) {
			System.out.println("No news was found for the selected criteria");
		} else {
			System.out.println(news.toString());
		}
		
	}
}