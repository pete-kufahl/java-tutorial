package com.prk.service;

import java.util.List;

public record ExternalPatientsManifest(List<Result> results, Info info) {
}