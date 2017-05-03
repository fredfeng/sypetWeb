public class Solution {

    public static boolean isPalindrome(sypet.ListNodeSyPet sypet_arg0) {

	gaohannk.PalindromeLinkedList sypet_var98 = new gaohannk.PalindromeLinkedList();
	gaohannk.ListNode sypet_var99 = null;
	if (sypet_arg0 != null)

	    {
		sypet.ListNodeSyPet cp25 = sypet_arg0;
		gaohannk.ListNode current25 = new gaohannk.ListNode(sypet_arg0.key);
		gaohannk.ListNode previous25 = current25;
		sypet_var99 = current25;
		while (cp25 != null) {
		    cp25 = cp25.nextNode;
		    if (cp25 != null) {
			current25 = new gaohannk.ListNode(cp25.key);
			previous25.next = current25;
			previous25 = current25;
		    } else
			previous25.next = null;
		}
	    }

	boolean sypet_var100 = sypet_var98.isPalindrome(sypet_var99);
	return sypet_var100;
    }
}
