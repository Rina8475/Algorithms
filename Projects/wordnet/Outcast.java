/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Outcast {
    private final WordNet net;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        net = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int dist = 0;
        String outcast = null;
        for (String a : nouns) {
            int temp = 0;
            for (String b : nouns) {
                if (a.equals(b)) {
                    continue;
                }
                temp += net.distance(a, b);
            }
            if (dist < temp) {
                dist = temp;
                outcast = a;
            }
        }
        return outcast;
    }
}
