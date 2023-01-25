package org.numamo.category.file.info.service.component.api.category.model;

import java.util.Objects;


/**
 * The model of code with respective user name
 *
 * @author Nazimov Aleksey I.
 */
public final class CodeDmo {

    private final String code;
    private final String name;

    public CodeDmo(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CodeDmo{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodeDmo)) return false;
        CodeDmo codeDmo = (CodeDmo) o;
        return Objects.equals(getCode(), codeDmo.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }
}
