package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public Integer insert(Credential credential) {
        return this.credentialMapper.insert(credential);
    }

    public Integer edit(Credential credential) {
        return this.credentialMapper.edit(credential);
    }

    public Integer delete(Integer credentialId) {
        return this.credentialMapper.delete(credentialId);
    }

    public List<Credential> selectUserCredentials(Integer userId) {
        var userCredentials = this.credentialMapper.selectCredentials(userId);
        for (var credential : userCredentials
        ) {
            credential.setEncryptedPassword(this.encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        }
        return userCredentials;
    }

    public Credential selectCredential(Integer credentialId) {
        return this.credentialMapper.selectCredential(credentialId);
    }
}
