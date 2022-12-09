/* eslint-disable @typescript-eslint/no-unused-vars */
/// <reference types="cypress" />

describe('Browse unauthorized', () => {
  it('Visit home page', () => {
    cy.visit('/');
  })

  it('Visit announcements page', () => {
    cy.visit('/announcements');
  });

  it('Visit meetings page', () => {
    cy.visit('/meetings');
  });

  it('Visit events page', () => {
    cy.visit('/events');
  });

  it('Visit eventsInactive page', () => {
    cy.visit('/eventsInactive');
  });

  it('Visit login page', () => {
    cy.visit('/login');
  });

  it('Visit terms of use page', () => {
    cy.visit('/termsOfUse');
  });

  it('Visit others profile', () => {
    cy.visit('/profile/1');
  });

  it('Visit other users posts', () => {
    cy.visit('/user/1/posts');
  });

  it('Visit chat', () => {
    cy.visit('/chat/1');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit chatrooms', () => {
    cy.visit('/chatrooms');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit chooseCategories', () => {
    cy.visit('/chooseCategories');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit enrolled', () => {
    cy.visit('/enrolled/1');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit myActivities', () => {
    cy.visit('/myActivities');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit my profile', () => {
    cy.visit('/profile');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit my posts', () => {
    cy.visit('/user/posts');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });

  it('Visit my inactive posts', () => {
    cy.visit('/user/postsInactive');

    cy.on('uncaught:exception', (_err, _) => { return false });

    cy.url().should('eq', 'http://localhost:5173/login');
  });
})