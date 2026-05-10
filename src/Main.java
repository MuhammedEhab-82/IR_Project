
void main() {
    try {
        SearchEngineController controller = new SearchEngineController();

        controller.initialize();

        controller.start();

    } catch (Exception e) {
        System.err.println("❌ Fatal error: " + e.getMessage());
        System.exit(1);
    }
}