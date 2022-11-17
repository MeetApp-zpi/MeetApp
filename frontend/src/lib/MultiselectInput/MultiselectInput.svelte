<script lang="ts">
    import Svelecte from 'svelecte';

    export let data;
    export let placeholder;
    export let inputId;
    export let valueBinder;
    export let style;
</script>

<div class="svelecte-multiselect">
    <Svelecte {style} options={data} {placeholder} {inputId} multiple="true" bind:value={valueBinder} />
</div>

<style>
    .svelecte-multiselect :global(.sv-content) {
        overflow-x: scroll;
    }
</style>
