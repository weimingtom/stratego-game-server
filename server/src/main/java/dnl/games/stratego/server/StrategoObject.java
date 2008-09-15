package dnl.games.stratego.server;

import java.io.Serializable;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;

/**
 * A {@code ManagedObject} that has a name and a description.
 */
public class StrategoObject implements Serializable, ManagedObject {
	private static final long serialVersionUID = 1L;

	private String name;

	private String description;

	public StrategoObject(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void setName(String name) {
		AppContext.getDataManager().markForUpdate(this);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		AppContext.getDataManager().markForUpdate(this);
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getName();
	}
}
