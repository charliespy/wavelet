import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.

    Set<String> var = new HashSet<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "This is a SeArCh EnGiNe (better than Google)";
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    if(var.contains(parameters[1])){
                        return "This element already exists!";
                    } else {
                        var.add(parameters[1]);
                    }
                    return "done! We have " + var.size() + " things now!";
                }
            } else if (url.getPath().contains("/search")) {
                if (url.getQuery().equals("s=app")) {
                    String front = "Things we have now: ";
                    String result = "";
                    Iterator itr = var.iterator();
                    while (itr.hasNext()) {
                        result += ", " + itr.next();
                    }
                    return front + result.substring(2);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
