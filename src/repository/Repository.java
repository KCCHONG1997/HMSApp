package repository;

public abstract class Repository {
	private static boolean isRepoLoaded = false;

    /**
     * Static method to load the repository data.
     * Calls the subclass-specific `loadData` implementation.
     *
     * @param repository An instance of a subclass of Repository
     * @return boolean indicating success or failure of the load operation
     */
    public static boolean loadRepository(Repository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository instance cannot be null.");
        }
        isRepoLoaded = repository.loadFromCSV();
        return isRepoLoaded;
    }

    /**
     * Checks if the repository is loaded.
     *
     * @return boolean indicating if the repository is loaded
     */
    public static boolean isRepoLoad() {
        return isRepoLoaded;
    }

    /**
     * Abstract method for loading data.
     * Each subclass must implement its own data loading logic.
     *
     * @return boolean indicating success or failure of the load operation
     */
    public abstract boolean loadFromCSV();

    /**
     * Clears repository data.
     */
    public void clearRepository() {
        isRepoLoaded = false;
        System.out.println("Repository cleared.");
    }
}