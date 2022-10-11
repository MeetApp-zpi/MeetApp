import { it, expect } from 'vitest';
import { render } from '@testing-library/svelte';
import Page from "./+page.svelte";

it('works', () => {
    expect(true);
    render(Page);
});