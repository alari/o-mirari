package ru.mirari.infra.file

class FileStorageService implements FileStorage {

    static transactional = false

    @Delegate FileStorage fileStorage
}
