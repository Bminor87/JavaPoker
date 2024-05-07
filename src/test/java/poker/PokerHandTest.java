package poker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class PokerHandTest {
    @Test
    void testHasFlush() {
        var flush = new PokerHand(List.of("♠1", "♠2", "♠8", "♠9", "♠10"));
        assertTrue(flush.hasFlush());
    }

    @Test
    void testHasFourOfAKind() {
        var hasFour = new PokerHand(List.of("♠1", "♦1", "♠1", "♠4", "♠1"));
        assertTrue(hasFour.hasFourOfAKind());
    }

    @Test
    void testHasFullHouse() {
        var fullHouse = new PokerHand(List.of("♠1", "♦1", "♠1", "♠2", "♦2"));
        assertTrue(fullHouse.hasFullHouse());
    }

    @Test
    void testHasPair() {
        var fullHouse = new PokerHand(List.of("♠1", "♦1", "♠2", "♠3", "♦4"));
        assertTrue(fullHouse.hasPair());
    }

    @Test
    void testHasRoyalFlush() {
        var royalFlush = new PokerHand(List.of("♠12", "♠10", "♠11", "♠1", "♠13"));
        assertTrue(royalFlush.hasRoyalFlush());
    }

    @Test
    void testHasStraight() {
        var straight = new PokerHand(List.of("♠2", "♦4", "♦3", "♠1", "♠5"));
        assertTrue(straight.hasStraight());
    }

    @Test
    void testHasStraightFlush() {
        var straight = new PokerHand(List.of("♠2", "♠4", "♠3", "♠1", "♠5"));
        assertTrue(straight.hasStraightFlush());
    }

    @Test
    void testHasThreeOfAKind() {
        var hasThree = new PokerHand(List.of("♠1", "♦1", "♠1", "♠4", "♠8"));
        assertTrue(hasThree.hasThreeOfAKind());
    }

    @Test
    void testHasTwoPairs() {
        var twoPairs = new PokerHand(List.of("♠1", "♦1", "♠8", "♠4", "♠8"));
        assertTrue(twoPairs.hasTwoPairs());
    }

    @Test
    void testToString() {
        var hand = new PokerHand(List.of("♠1", "♦1", "♠8", "♠4", "♠8"));
        assertEquals("♠1 ♦1 ♠8 ♠4 ♠8", hand.toString());
    }
}
