import java.util.ArrayList;

public class Deck
{
    private ArrayList<Card>unDealt;
    private ArrayList<Card>Dealt;

    public Deck(String[] ranks, String[] suits, int[] pointValues)
    {
        unDealt = new ArrayList<>();
        Dealt = new ArrayList<>();
        for(int i=0;i<ranks.length;i++)
        {
            for(int j=0;j<suits.length;j++)
            {
                for(int k=0;k<pointValues.length;k++)
                {
                    unDealt.add(new Card(ranks[i],suits[j],pointValues[k]));
                }
            }
        }
    }

    public ArrayList<Card> getUnDealt()
    {
        return unDealt;
    }

    public ArrayList<Card> getDealt()
    {
        return Dealt;
    }

    public boolean isEmpty()
    {
        if(unDealt.size() == 0)
        {
            return true;
        }
        return false;
    }

    public int size()
    {
        return unDealt.size();
    }

    public Card deal()
    {
        if(unDealt.size()>0)
        {
            Card temp = unDealt.get(0);
            Dealt.add(temp);
            unDealt.remove(0);
            return temp;
        }
        return null;
    }

    public void shuffle()
    {
        unDealt.addAll(Dealt);
        while(Dealt.size()>0)
        {
            Dealt.remove(0);
        }
        for(int i=unDealt.size()-1;i>0;i--)
        {
            int pos = (int) (Math.random() * i);
            Card temp = unDealt.get(i);
            unDealt.set(i,unDealt.get(i));
            unDealt.set(pos,temp);

        }
    }
}