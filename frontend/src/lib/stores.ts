import { writable } from 'svelte/store';

export const filteredCategoryIds = writable([]);
export const filteredLocationIds = writable([]);
export const sortingOption = writable(1);
export const nameSearchParam = writable(null);
export const userDetails = writable(null);
export const haveUnreadMessage = writable(false);

export function clearFilters() {
    filteredCategoryIds.set([]);
    filteredLocationIds.set([]);
    sortingOption.set(1);
    nameSearchParam.set(null);
}