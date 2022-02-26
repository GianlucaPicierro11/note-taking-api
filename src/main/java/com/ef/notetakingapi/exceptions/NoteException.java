package com.ef.notetakingapi.exceptions;

public class NoteException extends RuntimeException {

	private static final long serialVersionUID = -1828522005887320726L;

	public NoteException() {
		super();
	}

	public NoteException(String message) {
		super(message);
	}

	public NoteException(String message, Throwable cause) {
		super(message, cause);
	}

}
