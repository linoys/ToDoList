package il.ac.hit.model;
/**
 * 
 * @author Linoy and Liana
 *
 */
public class Item {
	private long itemId ;
	private String description;
	private long userId; 


	/**
	 *  ctor for Item object
	 */
	public Item() {}
	public Item(String description, long userId){
		setUserId(userId);
		setDescription(description);
	}
	/**
	 * get item id
	 * @return
	 */
	public long getItemId() {
		return itemId;
	}
	/**
	 * set item id
	 * @param idItem
	 */
	public void setItemId(long idItem) {
		this.itemId = idItem;
	}
	/**
	 * get description for the item
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * set description for the item
	 * @param description
	 * @return
	 */
	public boolean setDescription(String description) {
		if (description == null || description.isEmpty())
			return false;
		this.description = description;
		return true;	}
	/**
	 * get user id
	 * @return
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * set user id
	 * @param userId2
	 */
	public void setUserId(long userId2) {
		this.userId = userId2;
	}
	/**
	 * Override to string method
	 */
	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", description=" + description + ", userId=" + userId + "]";
	}



}
