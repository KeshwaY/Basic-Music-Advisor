package advisor.core.components;

public class Category extends AbstractComponent {

    private String name;

    public String getName() {
        return name;
    }

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }

    public static final class CategoryBuilder {
        private String name;

        private CategoryBuilder() {
        }

        public static CategoryBuilder aCategory() {
            return new CategoryBuilder();
        }

        public CategoryBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Category build() {
            Category category = new Category();
            category.name = name;
            return category;
        }
    }
}
