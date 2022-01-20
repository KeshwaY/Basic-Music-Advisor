package advisor.core.components;

public class Category extends AbstractComponent {

    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }

    public static final class CategoryBuilder {
        private String name;
        private String id;

        private CategoryBuilder() {
        }

        public static CategoryBuilder aCategory() {
            return new CategoryBuilder();
        }

        public CategoryBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CategoryBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public Category build() {
            Category category = new Category();
            category.name = name;
            category.id = id;
            return category;
        }
    }
}
