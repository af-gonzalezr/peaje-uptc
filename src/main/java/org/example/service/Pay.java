package org.example.service;

import org.example.Modelo.Orden;

public interface Pay {
    Result<Void> execute(Orden orden);
}
