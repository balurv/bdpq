package com.bdpq.FormData.model;

import jakarta.persistence.Entity;

public enum Gender {
    MALE {
        @Override
        public String toString() {
            return "MALE";
        }
    },
    FEMALE {
        @Override
        public String toString() {
            return null;
        }
    },
    OTHER {
        @Override
        public String toString() {
            return null;
        }
    };

    public abstract String toString();
}
