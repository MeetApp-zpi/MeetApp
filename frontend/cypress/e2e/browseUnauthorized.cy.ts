/* eslint-disable @typescript-eslint/no-unused-vars */
/// <reference types="cypress" />

describe('Browse unauthorized', () => {
  it('Visit home page', () => {
    cy.visit('http://localhost:5173');
  })

  it('Visit announcements page', () => {
    cy.visit('http://localhost:5173/announcements');
  });

  it('Visit meetings page', () => {
    cy.visit('http://localhost:5173/meetings');
  });

  it('Visit events page', () => {
    cy.visit('http://localhost:5173/events');
  });

  it('Visit eventsInactive page', () => {
    cy.visit('http://localhost:5173/eventsInactive');
  });

  it('Visit login page', () => {
    cy.visit('http://localhost:5173/login');
  });

  it('Visit terms of use page', () => {
    cy.visit('http://localhost:5173/termsOfUse');
  });

  it('Visit others profile', () => {
    cy.visit('http://localhost:5173/profile/1');
  });

  it('Visit other users posts', () => {
    cy.visit('http://localhost:5173/user/1/posts');
  });

  it('Visit chat', () => {
    cy.visit('http://localhost:5173/chat/1');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit chatrooms', () => {
    cy.visit('http://localhost:5173/chatrooms');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit chooseCategories', () => {
    cy.visit('http://localhost:5173/chooseCategories');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit enrolled', () => {
    cy.visit('http://localhost:5173/enrolled/1');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit myActivities', () => {
    cy.visit('http://localhost:5173/myActivities');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit my profile', () => {
    cy.visit('http://localhost:5173/profile');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit my posts', () => {
    cy.visit('http://localhost:5173/user/posts');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit my inactive posts', () => {
    cy.visit('http://localhost:5173/user/postsInactive');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });
})