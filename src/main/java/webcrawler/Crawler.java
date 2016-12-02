package webcrawler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	static HashSet<String> hSet = new HashSet<String>();
	static String domain;
	static int spaceCount = 0;
	public static void main(String[] args) throws InterruptedException {
	/*
	 * USe setProxy() if you are behind proxy.
	 */
	setProxy();
	domain = getDomainName(args[0]);
	System.out.println("domain = " + domain);
	System.out.println("\nSite Map :");
	crawl(args[0]);
	}

	public static void crawl(String url) {
		
		try {
			Document doc = Jsoup.connect(url).timeout(0).get();
			
			/* Look for all navigation links */
			Elements links = doc.select("a.wd-navbar-nav-elem-link");
			
			/* iterate through links, print the link only if it is not already navigated */
			for (Element link : links) {
				if (link.attr("href").contains(domain) && !hSet.contains(link.attr("abs:href"))) {
					hSet.add(link.attr("abs:href"));
					spaceCount+=4;
					String res = String.format("%"+ spaceCount + "s", "|-->"+link.attr("abs:href"));
					System.out.println(res);
					
					crawl(link.attr("abs:href"));
				}
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		spaceCount-=4;
	}


	public static String getDomainName(String url) {
	    URI uri=null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String domain = uri.getHost();
	    return domain.startsWith("www.") ? domain.substring(4) : domain;
	}
	
	public static void setProxy() {
		System.setProperty("http.proxyHost", "103.27.171.32");
		System.setProperty("http.proxyPort", "80");
	}
}
